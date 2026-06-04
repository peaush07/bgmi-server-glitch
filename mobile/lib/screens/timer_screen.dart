// Timer Screen - Main Countdown Display

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/key_provider.dart';

class TimerScreen extends StatefulWidget {
  const TimerScreen({Key? key}) : super(key: key);

  @override
  State<TimerScreen> createState() => _TimerScreenState();
}

class _TimerScreenState extends State<TimerScreen>
    with WidgetsBindingObserver {
  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  // Handle app lifecycle changes
  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    final keyProvider = context.read<KeyProvider>();
    
    if (state == AppLifecycleState.paused) {
      keyProvider.pauseCountdown();
    } else if (state == AppLifecycleState.resumed) {
      keyProvider.resumeCountdown();
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Countdown Timer'),
        centerTitle: true,
      ),
      body: Consumer<KeyProvider>(
        builder: (context, keyProvider, _) {
          if (keyProvider.selectedKey == null) {
            return Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Icon(
                    Icons.key_off,
                    size: 80,
                    color: Colors.grey[600],
                  ),
                  const SizedBox(height: 20),
                  Text(
                    'No Key Selected',
                    style: Theme.of(context).textTheme.headline6,
                  ),
                  const SizedBox(height: 10),
                  const Text('Select a key from your keys list to start'),
                ],
              ),
            );
          }

          final key = keyProvider.selectedKey!;
          final isExpired = keyProvider.secondsRemaining <= 0;

          return SingleChildScrollView(
            child: Padding(
              padding: const EdgeInsets.all(20.0),
              child: Column(
                children: [
                  // Server Info Card
                  Card(
                    child: Padding(
                      padding: const EdgeInsets.all(16.0),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text(
                                    'Server ID',
                                    style: Theme.of(context)
                                        .textTheme
                                        .caption,
                                  ),
                                  Text(
                                    key.serverId,
                                    style: Theme.of(context)
                                        .textTheme
                                        .headline6,
                                  ),
                                ],
                              ),
                              Container(
                                padding: const EdgeInsets.symmetric(
                                  horizontal: 12,
                                  vertical: 6,
                                ),
                                decoration: BoxDecoration(
                                  color: key.isTrial
                                      ? Colors.purple
                                      : Colors.blue,
                                  borderRadius: BorderRadius.circular(20),
                                ),
                                child: Text(
                                  key.isTrial ? 'FREE TRIAL' : 'PREMIUM',
                                  style: const TextStyle(
                                    color: Colors.white,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                              ),
                            ],
                          ),
                          const Divider(),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text(
                                    'Season',
                                    style: Theme.of(context)
                                        .textTheme
                                        .caption,
                                  ),
                                  Text(
                                    key.season,
                                    style: Theme.of(context)
                                        .textTheme
                                        .headline6,
                                  ),
                                ],
                              ),
                              Column(
                                crossAxisAlignment: CrossAxisAlignment.end,
                                children: [
                                  Text(
                                    'Status',
                                    style: Theme.of(context)
                                        .textTheme
                                        .caption,
                                  ),
                                  Text(
                                    isExpired ? 'EXPIRED' : 'ACTIVE',
                                    style: TextStyle(
                                      fontSize: 16,
                                      fontWeight: FontWeight.bold,
                                      color: isExpired
                                          ? Colors.red
                                          : Colors.green,
                                    ),
                                  ),
                                ],
                              ),
                            ],
                          ),
                        ],
                      ),
                    ),
                  ),
                  const SizedBox(height: 40),

                  // Countdown Timer Display
                  Stack(
                    alignment: Alignment.center,
                    children: [
                      // Circular Progress Indicator
                      SizedBox(
                        width: 250,
                        height: 250,
                        child: CircularProgressIndicator(
                          value: keyProvider.timePercentage,
                          strokeWidth: 8,
                          backgroundColor: Colors.grey[800],
                          valueColor: AlwaysStoppedAnimation<Color>(
                            isExpired ? Colors.red : Colors.green,
                          ),
                        ),
                      ),
                      // Timer Text
                      Column(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          Text(
                            keyProvider.formattedTime,
                            style: Theme.of(context).textTheme.headline3?.copyWith(
                              color: isExpired ? Colors.red : Colors.green,
                              fontWeight: FontWeight.bold,
                              fontFamily: 'Courier',
                            ),
                          ),
                          const SizedBox(height: 10),
                          Text(
                            isExpired ? 'TIME\'S UP!' : 'Remaining',
                            style: Theme.of(context).textTheme.subtitle1,
                          ),
                        ],
                      ),
                    ],
                  ),
                  const SizedBox(height: 40),

                  // Action Buttons
                  Row(
                    children: [
                      Expanded(
                        child: ElevatedButton.icon(
                          onPressed: !isExpired
                              ? () => keyProvider.pauseCountdown()
                              : null,
                          icon: const Icon(Icons.pause),
                          label: const Text('Pause'),
                        ),
                      ),
                      const SizedBox(width: 10),
                      Expanded(
                        child: ElevatedButton.icon(
                          onPressed: !isExpired
                              ? () => keyProvider.resumeCountdown()
                              : null,
                          icon: const Icon(Icons.play_arrow),
                          label: const Text('Resume'),
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 16),
                  SizedBox(
                    width: double.infinity,
                    child: ElevatedButton.icon(
                      onPressed: isExpired ? null : () {},
                      icon: const Icon(Icons.cloud_upload),
                      label: const Text('Sync with Server'),
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.orange,
                      ),
                    ),
                  ),

                  const SizedBox(height: 30),

                  // Key Details Card
                  Card(
                    child: Padding(
                      padding: const EdgeInsets.all(16.0),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            'Key Details',
                            style: Theme.of(context).textTheme.headline6,
                          ),
                          const Divider(),
                          _KeyDetailRow('Key ID', key.keyId),
                          _KeyDetailRow(
                            'Status',
                            key.isUsed
                                ? 'Used'
                                : (isExpired ? 'Expired' : 'Active'),
                          ),
                          _KeyDetailRow(
                            'Expires At',
                            key.formattedExpireTime,
                          ),
                          _KeyDetailRow(
                            'Created At',
                            key.createdAt.toLocal().toString().split('.')[0],
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            ),
          );
        },
      ),
    );
  }
}

class _KeyDetailRow extends StatelessWidget {
  final String label;
  final String value;

  const _KeyDetailRow(this.label, this.value);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(label),
          Flexible(
            child: Text(
              value,
              textAlign: TextAlign.end,
              style: const TextStyle(fontWeight: FontWeight.bold),
            ),
          ),
        ],
      ),
    );
  }
}
